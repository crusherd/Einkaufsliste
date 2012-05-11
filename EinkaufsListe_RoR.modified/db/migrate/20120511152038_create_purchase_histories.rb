class CreatePurchaseHistories < ActiveRecord::Migration
  def change
    create_table :purchase_histories do |t|
      t.date :purchaseDate
      t.references :shoppingList
      t.references :article

      t.timestamps
    end
    add_index :purchase_histories, :shoppingList_id
    add_index :purchase_histories, :article_id
  end
end
