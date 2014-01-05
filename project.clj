(defproject fiber "0.1.0-SNAPSHOT"
  :description "Displays information about how many households in a given Öresundskraft
 city district in Helsingborg or Höganäs have expressed interest in a network
 fiber connection."
  :url "https://github.com/ulsa/fiber"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [enlive "1.1.5"]]
  :main ^:skip-aot fiber.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
