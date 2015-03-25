(defproject lein-cljsbuild-example "1.2.3"
  :plugins [[lein-cljsbuild "1.0.5"]]
  :dependencies [[org.clojure/clojurescript "0.0-3126"]
                 [org.clojure/clojure "1.6.0"]
                 [reagent "0.5.0"]]
  :cljsbuild {
              :builds [{
                        :source-paths ["src"]
                        :compiler {:main example/core
                                   :output-to "out/init.js"
                                   :output-dir "out"
                                   :optimizations :none
                                   :source-map true
                                   }}]})
