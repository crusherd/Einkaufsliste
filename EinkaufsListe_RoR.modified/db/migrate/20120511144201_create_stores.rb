class CreateStores < ActiveRecord::Migration
  def change
    create_table :stores do |t|
      t.string :name
      t.references :address

      t.timestamps
    end
    add_index :stores, :address_id
  end
end
