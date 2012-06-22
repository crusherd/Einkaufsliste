class CreateShoppingLists < ActiveRecord::Migration
  def change
    create_table :shopping_lists do |t|
      t.date :creationDate
      t.string :name
      t.references :user
      t.references :article

      t.timestamps
    end
    add_index :shopping_lists, :user_id
    add_index :shopping_lists, :article_id
  end
  
  def self.down
    drop_table :shopping_lists
  end
end
