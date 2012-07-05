class Store < ActiveRecord::Base
  
  # relationships
  has_many :address, :dependent => :destroy
  has_and_belongs_to_many :article
  
  # owner
  
  # accessible attributes
  attr_accessible :name, :address_id, :address, :article, :article_id
  
  # validation
  validates :name, :presence => true, :allow_nil => false, :allow_blank => false
end
