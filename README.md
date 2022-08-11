# Crypto Ledger

![Heroku Badge](https://img.shields.io/badge/deployment-heroku-blueviolet) ![App Badge](https://img.shields.io/badge/app-android-brightgreen)

![Crypto Ledger Logo](https://raw.githubusercontent.com/TMDStudios/crypto_ledger/main/static/images/CryptoLedger.png)

Crypto Ledger is an open-source website that allows users to track their cryptocurrency trades. It was written in Java and uses the Spring framework. It is currently deployed on Heroku using a free tier account, which may lead to slow load times. The database makes use of ClearDB MySQL. Due to the limitations of the free version of the database, coin prices are stored in one large JSON file, which is then used to update price data.

The website is extended through the Crypto Ledger App, which makes use of the Rest API to allow users to manage their ledger.<br>The Crypto Ledger App can be found here: [Crypto Ledger App](https://github.com/TMDStudios/crypto_ledger_app_kotlin 'Crypto Ledger App')

You can see the website here: http://crypto-ledger.herokuapp.com/

## Key Features

- Track crypto currency trades with 'smart purchase prices'
- Access full transaction history for each coin
- View Live price data
- Quick overview of daily, weekly, and monthly price volatility
- Full API access  


## You may also like...

[Mock Trader](https://github.com/TMDStudios/MockTrader 'Mock Trader') - Open-source Bitcoin trading game

<br>

> NOTE: The original Python/Django version can be found here: https://github.com/TMDStudios/crypto_ledger
