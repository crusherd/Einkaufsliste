# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended to check this file into your version control system.

ActiveRecord::Schema.define(:version => 20120511152334) do

  create_table "addresses", :force => true do |t|
    t.string   "street"
    t.string   "zipCode"
    t.string   "city"
    t.string   "country"
    t.integer  "store_id"
    t.datetime "created_at", :null => false
    t.datetime "updated_at", :null => false
  end

  add_index "addresses", ["store_id"], :name => "index_addresses_on_store_id"

  create_table "articles", :force => true do |t|
    t.string   "name"
    t.decimal  "price"
    t.integer  "location_id"
    t.datetime "created_at",  :null => false
    t.datetime "updated_at",  :null => false
  end

  add_index "articles", ["location_id"], :name => "index_articles_on_location_id"

  create_table "purchase_histories", :force => true do |t|
    t.date     "purchaseDate"
    t.integer  "shoppingList_id"
    t.integer  "article_id"
    t.datetime "created_at",      :null => false
    t.datetime "updated_at",      :null => false
  end

  add_index "purchase_histories", ["article_id"], :name => "index_purchase_histories_on_article_id"
  add_index "purchase_histories", ["shoppingList_id"], :name => "index_purchase_histories_on_shoppingList_id"

  create_table "shopping_lists", :force => true do |t|
    t.date     "creationDate"
    t.integer  "owner_id"
    t.integer  "article_id"
    t.datetime "created_at",   :null => false
    t.datetime "updated_at",   :null => false
  end

  add_index "shopping_lists", ["article_id"], :name => "index_shopping_lists_on_article_id"
  add_index "shopping_lists", ["owner_id"], :name => "index_shopping_lists_on_owner_id"

  create_table "stores", :force => true do |t|
    t.string   "name"
    t.integer  "address_id"
    t.datetime "created_at", :null => false
    t.datetime "updated_at", :null => false
  end

  add_index "stores", ["address_id"], :name => "index_stores_on_address_id"

  create_table "users", :force => true do |t|
    t.string   "username"
    t.integer  "shoppingList_id"
    t.datetime "created_at",      :null => false
    t.datetime "updated_at",      :null => false
  end

  add_index "users", ["shoppingList_id"], :name => "index_users_on_shoppingList_id"

end
