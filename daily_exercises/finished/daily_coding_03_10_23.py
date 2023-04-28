""" Daily problem: You are given a 2d-array of exhange rates on currencies.
    Determine whether there is a possible arbitrage. I.E. whether there
    is a sequence of trades where starting at amount A of a currency
    you can trade into some amount greater than A currency.
    
    There are no transaction costs and you can trade fractions of a currency."""


def daily_problem(exchange_rates):
    """ Simply checks each exchange for any gain,
        if not equal exchange, will return True"""
    busted = False
    for currency_index in range(len(exchange_rates)):
        for exchange_index in range(len(exchange_rates[currency_index])):
            if exchange_rates[currency_index][exchange_index] * exchange_rates[exchange_index][currency_index] != 1:
                busted = True
    return busted

if __name__ == "__main__":
    print(daily_problem([[1,0.1],[10,1]]))
    print(daily_problem([[10,1],[10,1]]))