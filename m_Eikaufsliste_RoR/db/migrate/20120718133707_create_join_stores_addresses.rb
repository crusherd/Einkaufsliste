class CreateJoinStoresAddresses < ActiveRecord::Migration
  def up
    create_table :addresses_stores, :id => false do |t|
      t.integer :address_id
      t.integer :store_id
    end
  end

  def down
    drop_table :address_stores
  end
end
