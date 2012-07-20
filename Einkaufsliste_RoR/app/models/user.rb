class User < ActiveRecord::Base
  attr_accessible :username
  validates :username, :presence => true, :allow_nil => false, :allow_blank => false, :uniqueness => true
  has_many :shoppinglists, :dependent => :destroy
end