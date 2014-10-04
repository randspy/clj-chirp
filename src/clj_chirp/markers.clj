(ns clj-chirp.markers)

(def post-marker "->")

(def follow-marker "follows")

(def wall-marker "wall")

(def all-markers [post-marker follow-marker wall-marker])

(defn- token-position-in-text [text token]
  (let [position (.indexOf text token)]
    (if (< -1 position)
      {:token token :token-position position}
      nil)))

(defn first-marker-in-text? [text token]
  (let [tokens-with-their-position
        (remove nil?
                (map #(token-position-in-text text %) all-markers))]
    (= token (:token (first (sort-by :token-position tokens-with-their-position))))))
