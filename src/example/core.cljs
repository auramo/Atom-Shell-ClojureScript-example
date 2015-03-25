(ns example.core
  (:require [reagent.core :as reagent :refer [atom]]))

(defonce fs (js/require "fs"))
(defonce walk (js/require "walk"))

(enable-console-print!)

(defonce output (atom ""))
(defonce filespec (atom "/tmp"))

(defn handle-file [root stat next]
  (println "handle file")
  (reset! output (str @output root "/" (.-name stat) "\n"))
  (next))

(defn walk-dir []
  (reset! output "")
  (let [walker (.walk walk @filespec)]
    (.on walker "file" handle-file)
    (.on walker "end" #(println "finished"))))

(defn create-file-callback-handler [err]
  (if err
    (reset! output (str err))
    (reset! output "OK")))

(defn create-file []
  (reset! output "")
  (.writeFile fs @filespec "Hello" create-file-callback-handler))

(defn output-component []
  [:pre#output @output])

(defn app []
  [:div
   [:h2 "Atom Shell ClojureScript Tester"]
   [:input#filespec {:type "text" :on-change #(reset! filespec (-> % .-target .-value)) :value @filespec}]
   [:button {:name "create-file" :on-click create-file} "Create file"]
   [:button {:name "walk-dir" :on-click walk-dir} "Walk dir"]
   (output-component)
   ])

(reagent/render [app] (.-body js/document))
