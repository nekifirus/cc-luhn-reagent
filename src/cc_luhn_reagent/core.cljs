(ns cc-luhn-reagent.core
  (:require
   [clojure.string :as str]
   [clojure.edn :as edn]
   [reagent.core :as r]
   [reagent.dom :as d]))

(defonce store (r/atom ""))

;; -----------------------
;; Validation algorithm
;;

(defn double-number [number]
  "Double given number. If result more than 9 will sub 9 from result"
  (let [doubled (* 2 number)]
    (if (> doubled 9) (- doubled 9) doubled)))

(defn double-odd [sequence]
  "Doubles specific-way every second number in given sequence"
  (map-indexed
   #(if (odd? %1)
      (double-number (edn/read-string %2))
      (edn/read-string %2))
   sequence))

(defn luhn-valid? [cc-string]
  "Check cc-string by Luhn algorithm"
  (when (> (count cc-string) 1)
    (zero? (rem (->> cc-string
                     (seq)
                     (rseq)
                     (double-odd)
                     (reduce +))
                10))))


;; -------------------------
;; Helpers
;;

(defn event-handler [new-value store]
  "Handle user input"
  (reset! store (str/replace new-value #"\D*" "")))
  
(defn input-style [store]
  (case (luhn-valid? @store)
    true "valid"
    false "invalid"
    nil nil))

;; -------------------------
;; Components
;;

(defn header [] [:div.header "Ultimate cc-checker"])

(defn input [store]
  "Main credit card input component"
  (let [style (input-style store)]
    [:input {:type "text"
           :maxLength "19"
           :value @store
           :class style
           :placeholder "Enter credit card number here"
           :on-change #(event-handler (-> % .-target .-value) store)}]))

(defn footer []
  [:div.footer
   "Learn more about "
   [:a {:href "https://en.wikipedia.org/wiki/Luhn_algorithm"} "Luhn algorithm"]])

;; -------------------------
;; Views

(defn home-page []
  [:<>
   [header]
   [input store]
   [footer]
   ]) 
;; -------------------------
;; Initialize app

(defn mount-root []
  (d/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
