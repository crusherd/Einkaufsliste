class CreateUsers < ActiveRecord::Migration
  def change
    create_table :users do |t|
      t.string :username
      t.references :shoppingList

      t.timestamps
    end
    add_index :users, :shoppingList_id
  end
  
  def self.down
    drop_table :users
  end
end
