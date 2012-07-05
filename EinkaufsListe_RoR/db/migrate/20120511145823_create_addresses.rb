class CreateAddresses < ActiveRecord::Migration
  def change
    create_table :addresses do |t|
      t.string :street
      t.string :zipCode
      t.string :city
      t.string :country
      t.references :store

      t.timestamps
    end
    add_index :addresses, :store_id
  end
  
  def down
    drop_table :addresses
  end
end
