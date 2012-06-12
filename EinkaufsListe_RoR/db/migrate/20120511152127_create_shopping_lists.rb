class CreateShoppingLists < ActiveRecord::Migration
  def change
    create_table :shopping_lists do |t|
      t.date :creationDate
      t.references :owner
      t.references :article

      t.timestamps
    end
    add_index :shopping_lists, :owner_id
    add_index :shopping_lists, :article_id
  end
  
  def down
    drop_table :users
  end
end
