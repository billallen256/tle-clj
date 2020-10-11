(defproject tle-clj "0.1.0-SNAPSHOT"
  :description "A Clojure library designed to handle Two Line Element (TLE) sets for calculating orbital parameters, usually for satellites."
  :url "http://github.com/gershwinlabs/tle-clj"
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.flatland/useful "0.9.0"]
                 [org.clojure/math.numeric-tower "0.0.4"]]
  :repl-options {:init-ns tle-clj.core})
