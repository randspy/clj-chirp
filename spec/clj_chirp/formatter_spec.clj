(ns clj-chirp.formatter-spec
  (:require [speclj.core :refer :all]
            [clj-chirp.formatter :refer :all]))

(describe "clj-chirp.formatter"
          (it "Nothing to format."
              (should= ""
                       (format-post {})))
          (describe "Post to format"
                    (it "Time unit is singular"
                        (should= "post (1 second ago)"
                                 (format-post {:content "post" :time-diff 1 :time-unit "second"})))
                    (it "Time unit is prular"
                        (should= "post (2 seconds ago)"
                                 (format-post {:content "post" :time-diff 2 :time-unit "second"}))))
          (describe "Post with user name to format."
                    (it "User name is added in the front"
                        (should= "bob - post (1 second ago)"
                                 (format-post
                                   {:user-name "bob" :content "post" :time-diff 1 :time-unit "second"})))))