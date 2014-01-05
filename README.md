# fiber

Displays information about how many households in a given Öresundskraft
city district in Helsingborg or Höganäs have expressed interest in a network
fiber connection.

As of 2014-01-05, these are the id ranges that I've managed to figure out:

* 3616-3832,4670 (Helsingborg)
* 4376-4559 (Höganäs)

## Installation

Make sure [Leiningen 2+](http://leiningen.org/) is installed.

Build a stand-alone executable jar like this:

    $ lein uberjar

## Usage

The utility can be run either from Leiningen or as a stand-alone jar:

    $ lein run -- [args]

    $ java -jar fiber-<version>-standalone.jar [args]

## Options

FIXME: listing of options this app accepts.

## Examples

If no arguments are given, the system displays information about the default
city district, 3801 (where I live):

    $ lein run
    ({:id 3801,
      :name "Sofieberg Södra",
      :addresses 370,
      :interested 36,
      :interested-percent 10})

If one argument is given, information about that city district is displayed:

    $ lein run -- 3800
    ({:id 3800,
      :name "Veckogatan Adolfsberg",
      :addresses 102,
      :interested 0,
      :interested-percent 0})

Multiple arguments will give information about all those city districts, excluding
the ids that don't map to any city district:

    $ lein run -- 3797 3798 3799
    ({:id 3797,
      :name "Rydebäck 2",
      :addresses 202,
      :interested 7,
      :interested-percent 3}
     {:id 3799,
      :name "Vasatorp Trädgård",
      :addresses 27,
      :interested 5,
      :interested-percent 19})

Note that there is a sleep of 200-500 ms between calls, in order not to swamp
the target server. Please be responsible when using multiple ids.

### Future Enhancements

* Be able to specify ranges, like `--from 3800 --to 3900` or `--from 3800 --count 50`
* Not everyone knows the id of their city district. It would be nice to be able
  to query a nightly updated Datomic database for this information.

## License

Copyright © 2014 Ulrik Sandberg

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
