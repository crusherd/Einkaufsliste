class CreateArticles < ActiveRecord::Migration
  def change
    create_table :articles do |t|
      t.text :name
      t.decimal :price
      t.references :location
      t.references :shoppingList

      t.timestamps
    end
    add_index :articles, :shoppingList_id
    add_index :articles, :location_id
  end
  
  def down
    drop_table :articles
  end
end
