(ns clj-chirp.formatter)

(defn- singular? [post]
  (= 1 (:time-diff post)))

(defn- formant-user-name [post]
  (if (contains? post :user-name)
    (str (:user-name post) " - ")
    ""))

(defn format-post [post]
  (if (seq post)
    (let [time-unit-word-ending (if (singular? post) "" "s")]
      (str (formant-user-name post)
           (:content post) " ("
           (:time-diff post) " "
           (:time-unit post) time-unit-word-ending " ago)"))
    ""))

(defn- one-formatted-post [post time-diffrence timestamp]
  (let [time-diff (time-diffrence (:timestamp post) timestamp)]
    (str (format-post (merge post time-diff)) "\n")))

(defn formatted-posts [posts time-diffrence timestamp]
  (let [sorted-posts (reverse (sort-by :timestamp posts))]
    (reduce str (map #(one-formatted-post % time-diffrence timestamp) sorted-posts))))
