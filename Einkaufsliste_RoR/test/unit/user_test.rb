require 'test_helper'

class UserTest < ActiveSupport::TestCase
  test "required attributes" do
    user = User.new
    assert !user.valid?
    assert user.errors.get(:username)
  end
  
  test "unique_user" do
    user = User.new(:username => "mahieke")
    assert !user.save
  end
  
  test "create and delete an user" do
    user = User.new(:username => "John Doe")
    
    assert user.save
    
    assert user.delete
  end
  
  test "delete existing user with associated shoppinglist" do
    user = users(:rodancza)
    
    associated_shoppinglists = user.shoppinglists.count
    
    existing_shoppinglists = users.count
    
    user.delete
    
    assert_equal(users.count,existing_shoppinglists - associated_shoppinglists)
  end
end
