(ns clj-chirp.content)


(defn- trim [string]
  (.trim (if (empty? string) "" string)))

(defn split-user-name-from-content [user-input split-token]
  (let [number-of-splited-elements 2
        tokens (clojure.string/split
                 user-input (re-pattern split-token) number-of-splited-elements)
        user-name (first tokens)
        post (second tokens)]
    {:user-name (trim user-name) :content (trim post)}))