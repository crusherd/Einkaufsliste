class User < ActiveRecord::Base
  belongs_to :shoppingList
  attr_accessible :username
end
