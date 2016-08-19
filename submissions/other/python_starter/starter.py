import random
from functools import total_ordering


@total_ordering
class Stock:
    @staticmethod
    def parse(string: str):
        return Stock(*map(int, string.split(":")))

    def __init__(self, stock_type: int, amount: int):
        self.type = stock_type
        self.amount = max(amount, 0)

    def __str__(self):
        return str(self.type)+":"+str(self.amount)

    def __eq__(self, other):
        return self.amount == other.type

    def __lt__(self, other):
        return self.amount < other.amount

    def update(self, amount) -> 'Stock':
        return Stock(self.type, amount)

    def __mul__(self, other: int) -> 'Stock':
        return self.update(self.amount*other)

    def __floordiv__(self, other: int) -> 'Stock':
        return self.update(self.amount//other)

    def __add__(self, other: int) -> 'Stock':
        return self.update(self.amount+other)

    def __sub__(self, other: int) -> 'Stock':
        return self.update(self.amount-other)


class Offer:
    @staticmethod
    def parse(string: str) -> 'Offer':
        return Offer(*map(Stock.parse, string.split(",")))

    def __init__(self, offer: Stock, payment: Stock):
        self.offer = offer
        self.payment = payment

    def __str__(self):
        return str(self.offer)+","+str(self.payment)


def read_stock_value(value: str):
    global hidden_price, hidden_stock
    stock, price = value.split(":")
    hidden_price = float(price)
    hidden_stock = int(stock)


def process_input():
    handlers = {
        "SecretValue": read_stock_value,
        "RandomSeed": read_seed,
        "MakeOffer": make_offer,
        "AcceptOffer": accept_offer,
        "AcceptedOffers": accepted_offers,
    }
    method = input().strip()
    data = input().strip()
    output = handlers[method](data)
    if output is not None:
        print(str(output))
    else:
        print()


def read_seed(seed: str):
    random.seed(int(seed))


def start():
    while True:
        process_input()


hidden_stock = None
hidden_price = None


def make_offer(current_stock: str):
    current_stock = map(Stock.parse, current_stock.split(";"))
    pass


def accept_offer(available_offers: str):
    available_offers = map(Offer.parse, available_offers.split(";"))
    pass


def accepted_offers(offers: str):
    offers = map(Offer.parse, offers.split(";"))
    pass


if __name__ == "__main__":
    start()