(ns clj-chirp.formatter)

(defn- singular? [post]
  (= 1 (:time-diff post)))

(defn format-post [post]
  (if (< 0 (count post))
    (let [time-unit-word-ending (if (singular? post) "" "s")]
      (str (:content post) " ("
           (:time-diff post) " "
           (:time-unit post) time-unit-word-ending " ago)"))
    ""))