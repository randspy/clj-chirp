(ns clj-chirp.posting-spec
  (:require [speclj.core :refer :all]
            [clj-chirp.posting :refer :all]))

(describe "User post a message."
          (describe "User text is empty."
                    (it "Returns empty value."
                        (should= {:user "" :post ""}
                                 (post "->" "->"))))
          (describe "User text contains only user name."
                    (it "Returns a user with empty post"
                        (should= {:user "user bob" :post ""}
                                 (post "user bob ->" "->"))))
          (describe "Expected data."
                    (it "Returns a user with a post."
                        (should= {:user "user bob" :post "post"}
                                 (post "user bob -> post" "->"))))
          (describe "There are more tokens inside text."
                    (it "Returns a user with a post."
                        (should= {:user "user bob" :post "post ->"}
                                 (post "user bob -> post ->" "->")))))