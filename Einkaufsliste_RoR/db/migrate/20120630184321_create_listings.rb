class CreateListings < ActiveRecord::Migration
  def change
    create_table :listings do |t|
      t.integer :amount
      t.integer :shoppinglist_id
      t.string :article_id

      t.timestamps
    end
  end
end
