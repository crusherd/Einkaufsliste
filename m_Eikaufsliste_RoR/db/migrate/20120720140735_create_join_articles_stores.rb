class CreateJoinArticlesStores < ActiveRecord::Migration
  def up
    create_table :articles_stores, :id => false do |t|
      t.integer :article_id
      t.integer :store_id
    end
  end

  def down
    drop_table :articles_stores
  end
end
