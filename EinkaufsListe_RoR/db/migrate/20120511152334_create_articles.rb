class CreateArticles < ActiveRecord::Migration
  def change
    create_table :articles do |t|
      t.text :name
      t.decimal :price
      t.references :location

      t.timestamps
    end
    add_index :articles, :location_id
  end
  
  def down
    drop_table :users
  end
end
