class Shoppinglist < ActiveRecord::Base
  # accessible attributes 
  attr_accessible :creationDate, :name, :user_id
  
  # validation
  validates :name, presence: true, :allow_nil => false, :allow_blank => false
  validates :user_id, presence: true, :allow_nil => false, :allow_blank => false 
  
  # relationships
  belongs_to :user
  has_and_belongs_to_many :articles
end
