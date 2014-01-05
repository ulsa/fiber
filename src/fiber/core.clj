(ns fiber.core
  (:require [net.cgrand.enlive-html :as html]
            [clojure.string :refer [split blank?]]
            [clojure.pprint :refer [pprint]])
  (:gen-class))

(defn fetch-url
  ([url]
     (fetch-url url "UTF-8"))
  ([url encoding]
     (-> url java.net.URL. .getContent (java.io.InputStreamReader. encoding) html/html-resource)))

(def ^:dynamic *base-url* "http://stadsnat.oresundskraft.se/connect/connect.php?id=%d")

(def ^:dynamic *encoding* "ISO-8859-1")

(defn build-url [n]
  (format *base-url* n))

(defn fetch [n]
  (fetch-url (build-url n) *encoding*))

(def ^:dynamic *name-selector* [:#module_statistics :div [:table (html/nth-of-type 1)] [:tr (html/nth-of-type 1)] [:td (html/nth-of-type 2)]])

(def ^:dynamic *addresses-selector* [:#module_statistics :div [:table (html/nth-of-type 1)] [:tr (html/nth-of-type 3)] [:td (html/nth-of-type 2)]])

(def ^:dynamic *interested-selector* [:#module_statistics :div [:table (html/nth-of-type 2)] [:td (html/nth-of-type 3)] :span])

(def ^:dynamic *interested-percent-selector* [:#module_statistics :div [:table (html/nth-of-type 2)] [:td (html/nth-of-type 4)]])

(defn to-num [s]
  (if (blank? s) 0 (Integer/parseInt s)))

(defn extract [id node]
  (let [name (-> node (html/select *name-selector*) first html/text)
        addresses (-> node (html/select *addresses-selector*) first html/text to-num)
        interested (-> node (html/select *interested-selector*) first html/text (split #"\s") first to-num)
        interested-percent (-> node (html/select *interested-percent-selector*) first html/text (split #"%") first to-num)]
    {:id id
     :name name
     :addresses addresses
     :interested interested
     :interested-percent interested-percent}))

(defn fetch-all [ids]
  (filter
   #(not (blank? (:name %)))
   (doall
    (for [n ids :let [n (if (string? n) (Integer/parseInt n) n)]]
      (do
        (let [ms (+ 200 (rand-int 300))]
          (Thread/sleep ms))
        (extract n (fetch n)))))))

(defn -main
  "Give zero or more ids for city areas, eg 3791 or 4557 4558 4559.
 Default is 3801 for Sofieberg Södra"
  [& args]
  (pprint
   (fetch-all (or args [3801]))))
