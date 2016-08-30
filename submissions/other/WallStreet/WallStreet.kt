
import java.io.FileOutputStream
import java.io.PrintStream
import java.util.*

val LOGGER = PrintStream(FileOutputStream("log.txt", true))

data class Stock(val type : Int, val amount : Int) {

    operator fun minus(amount : Int) = copy(amount = this.amount - amount)
    operator fun plus(amount: Int) = copy(amount = this.amount + amount)
    fun setAmount(amount: Int) = copy(amount = amount)

    operator fun minus(other : Stock) : Stock {
        assert(type == other.type)
        return copy(amount = this.amount - other.amount)
    }

    operator fun plus(other : Stock) : Stock {
        assert(type == other.type)
        return copy(amount = this.amount + other.amount)
    }

    override fun toString() = "$type:$amount"
}

data class Offer(val offer: Stock, val payment: Int) {
    override fun toString() = "$offer@$payment"
}

fun parseStock(repr : String) : Stock {
    val data = repr.split(":").map { it.toInt() }
    return Stock(data[0], data[1])
}

fun parseOffer(repr: String) : Offer {
    val data = repr.split("@")
    return Offer(parseStock(data[0]), data[1].toInt())
}

fun parseOffers(repr: String) = if (repr == "") emptyList<Offer>() else repr.split(";").map { parseOffer(it) }


interface Player {
    fun secretValue(stockType: Int, value: Int)
    fun makeOffer(currentStock: List<Stock>) : Offer?
    fun acceptOffer(offers: List<Offer>) : Offer?
    fun acceptedOffers(offers: List<Offer>)

    var random : Random
}

fun main(args : Array<String>) {

    try {
        //Change bot name here
        val player: Player = WallStreet()

        while (true) {
            val function = readLine()
            function ?: return
            val line = readLine()!!
            //Uncomment below if having problems.
//            LOGGER.println(function)
//            LOGGER.println(line)
            val result = when (function) {
                "SecretValue" -> {
                    val data = line.split(":").map { it.toInt() }
                    player.secretValue(data[0], data[1])
                }
                "MakeOffer" -> player.makeOffer(line.split(";").map { parseStock(it) }) ?: ""
                "AcceptOffer" -> player.acceptOffer(parseOffers(line)) ?: ""
                "AcceptedOffers" -> player.acceptedOffers(parseOffers(line))
                "RandomSeed" -> player.random = Random(line.toLong())
                else -> return        //Exit program
            }

            println(if (result == Unit) "" else result)
        }
    } catch (e : Exception) {
        e.printStackTrace(LOGGER)
    } finally {
        LOGGER.close()
    }
}


// ###################################################
// #          Put program logic below here.          #
// ###################################################


const val DEFAULT_STOCK_VALUE = 333
const val MAX_TURNS = 1000
const val MAX_STOCK_VALUE = 1000

class WallStreet : Player {

    var secretStockType = 0
    var secretStockValue = 0
    override var random = Random()


    var turn = 0
    val stockPriceStatistics = mutableMapOf<Int, DoubleSummaryStatistics>()

    override fun secretValue(stockType: Int, value: Int) {
        secretStockType = stockType
        secretStockValue = value
    }

    override fun makeOffer(currentStock: List<Stock>): Offer {
        val stock = currentStock[random.nextInt(currentStock.size)]
        val type = stock.type
        val amount = random.nextInt(stock.amount)
        val price = getSellPrice(type) * amount
        return Offer(Stock(type, amount), Math.ceil(price).toInt())
    }

    override fun acceptOffer(offers: List<Offer>): Offer? {
        var bestOffer : Offer? = null
        var mostProfit = 0.0
        for (offer in offers) {
            val offerProfit = profitOfOffer(offer)
            if (offerProfit > mostProfit) {
                bestOffer = offer
                mostProfit = offerProfit
            }
        }
        return bestOffer
    }

    override fun acceptedOffers(offers: List<Offer>) {
        for ((stock, payment) in offers) {
            val stats = stockPriceStatistics.getOrPut(stock.type) { DoubleSummaryStatistics() }
            for (i in 1..stock.amount) {
                stats.accept(payment.toDouble() / stock.amount)
            }
        }
    }

    private fun getSellPrice(type: Int): Double {
        var price = getPrice(type)
        if (price < 1000) {
            price += (1000 - price) * (MAX_TURNS - turn) / MAX_TURNS
        }
        return if (type == secretStockType) Math.max(secretStockValue.toDouble(), price) else price
    }

    private fun getPrice(type: Int): Double {
        return stockPriceStatistics[type]?.average ?: DEFAULT_STOCK_VALUE.toDouble()
    }

    private fun profitOfOffer(offer: Offer): Double {
        return getBuyPrice(offer.offer.type) * offer.offer.amount - offer.payment
    }

    private fun getBuyPrice(type: Int): Double {
        var price = getPrice(type)
        price = price * turn / MAX_TURNS
        return if (type == secretStockType) Math.min(secretStockValue.toDouble(), price) else Math.min(price, MAX_STOCK_VALUE.toDouble())
    }

}