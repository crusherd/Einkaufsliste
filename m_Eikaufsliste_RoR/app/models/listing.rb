class Listing < ActiveRecord::Base
  attr_accessible :amount, :article_id, :shoppinglist_id
  belongs_to :shoppinglist
  belongs_to :article
end
