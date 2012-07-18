class CreateAddresses < ActiveRecord::Migration
  def change
    create_table :addresses do |t|
      t.string :street
      t.string :zipCode
      t.string :city

      t.timestamps
    end
  end
end
