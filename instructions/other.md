All calls consist of two lines: The first line is the function being called: `SecretValue`, `MakeOffer`, `AcceptOffer`, `AcceptedOffers`, `SetRandom`, and the second line containing the actual data.

- Stocks are formatted with a `:` delimiter: `stockType:stockAmount`.
- Offers are formatted with a `,` delimiter: `offer,payment`
- Lists are formatted with a `;` delimiter
- `SecretValue` is formatted with a `:` delimiter: `stockType:value`

`SetRandom` is used to make your submission deterministic.  If your submission uses randomness, please use the integer value passed as the seed!

All function calls *need* a response.  If the response is `null` or `void`, return an empty string.

Your submission should go in the `/other/bot_name` folder.  The folder needs to contain a `command.txt`, which contains a list of commands that can be used to start your submission.
