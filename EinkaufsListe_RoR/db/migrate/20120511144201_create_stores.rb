class CreateStores < ActiveRecord::Migration
  def change
    create_table :stores do |t|
      t.string :name
      t.references :address
      t.references :article
      
      t.timestamps
    end
    add_index :stores, :address_id
    add_index :stores, :article_id
  end
  
  def down
    drop_table :users
  end
end
