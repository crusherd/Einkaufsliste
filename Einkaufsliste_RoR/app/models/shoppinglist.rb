class Shoppinglist < ActiveRecord::Base
  # accessible attributes 
  attr_accessible :creationDate, :name, :user_id
  
  # validation
  validates :name, :presence => true, :allow_nil => false, :allow_blank => false
  validates :user_id, :presence => true, :allow_nil => false, :allow_blank => false
  validates :creationDate, :presence => true, :allow_nil => false, :allow_blank => false
  # validate uniqueness of List
  validates :name, :uniqueness => true
  
  # relationships
  belongs_to :user
  has_many :listings, :dependent => :destroy
  has_many :articles, :through => :listings
end
