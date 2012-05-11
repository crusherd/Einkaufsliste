class ShoppingList < ActiveRecord::Base
  belongs_to :owner
  belongs_to :article
  attr_accessible :creationDate
end
