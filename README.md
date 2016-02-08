# oauth-example

Example application illustrating Twitter OAuth Sign In

## Prerequisites

You will need [Leiningen][1] 2.0 or above installed. Twitter account and a [Twitter application](https://apps.twitter.com/app) created as described [here](https://dev.twitter.com/web/sign-in/desktop-browser).

[1]: https://github.com/technomancy/leiningen



## Running

To start a web server for the application, run:

    lein run

The application needs the Twitter consumer key and the secret to be present in the environment:

    :twitter-consumer-key "your twitter consumer key"
    :twitter-consumer-secret "your twitter secret key"

These are found on your Twitter app pagge.

## License

Copyright © 2016 FIXME
