class PurchaseHistory < ActiveRecord::Base
  belongs_to :shoppingList
  belongs_to :article
  attr_accessible :purchaseDate
end
