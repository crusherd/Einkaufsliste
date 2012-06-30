class CreateShoppinglists < ActiveRecord::Migration
  def change
    create_table :shoppinglists do |t|
      t.string :name
      t.date :creationDate
      t.integer :user_id

      t.timestamps
    end
  end
end
