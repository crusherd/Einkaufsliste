class CreateJoinArticlesShoppinglists < ActiveRecord::Migration
  # self. wurde nachtraeglich hinzugefuegt: nach Gallileo Computing Anleitung
  def self.up
    create_table :articles_shoppinglists, :id => false do |t|
      t.integer :article_id
      t.integer :shoppinglist_id
    end
  end

  # self. wurde nachtraeglich hinzugefuegt: nach Gallileo Computing Anleitung
  def self.down
    drop_table :articles_shoppinglists
  end
end
