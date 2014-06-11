# Robots

## Wow

You've found one of my very first clojure projects from way back in
the day. Don't judge me. ;)

## Running

First you need to compile your robots, then you run the
`robots.core/fight` function and give it the classname of the robots
you want to fight.

So for the example robot in `src/robots/fatrobot.clj` run the
following:

    lein compile robots.fatrobot
    lein run -m robots.core/fight robots.fatrobot robots.fatrobot
