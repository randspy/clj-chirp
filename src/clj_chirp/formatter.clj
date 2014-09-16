(ns clj-chirp.formatter)

(defn- singular? [post]
  (= 1 (:time-diff post)))

(defn format-post [post]
  (if (seq post)
    (let [time-unit-word-ending (if (singular? post) "" "s")]
      (str (:content post) " ("
           (:time-diff post) " "
           (:time-unit post) time-unit-word-ending " ago)"))
    ""))

(defn format-post-with-user-name [post]
  (str (:user-name post) " - " (format-post post)))