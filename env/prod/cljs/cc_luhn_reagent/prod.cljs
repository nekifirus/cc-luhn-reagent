(ns cc-luhn-reagent.prod
  (:require
    [cc-luhn-reagent.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
