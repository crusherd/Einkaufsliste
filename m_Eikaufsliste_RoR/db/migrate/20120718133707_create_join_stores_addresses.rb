class CreateJoinStoresAddresses < ActiveRecord::Migration
  def up
    create_table :stores_addresses, :id => false do |t|
      t.integer :stores_id
      t.integer :addresses_id
    end
  end

  def down
    drop_table :stores_addresses
  end
end
