class CreateAddresses < ActiveRecord::Migration
  def change
    create_table :addresses do |t|
      t.string :street
      t.string :zipcode
      t.string :city

      t.timestamps
    end
  end
end
