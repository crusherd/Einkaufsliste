class CreatePurchaseHistories < ActiveRecord::Migration
  def change
    create_table :purchase_histories do |t|
      t.date :purchaseDate
      t.references :article

      t.timestamps
    end
    add_index :purchase_histories, :article_id
  end
  
  def down
    drop_table :users
  end
end
