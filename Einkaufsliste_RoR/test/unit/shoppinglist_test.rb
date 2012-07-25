require 'test_helper'

class ShoppinglistTest < ActiveSupport::TestCase
  test "required attributes" do
    shoppinglist = Shoppinglist.new
    assert !shoppinglist.valid?
    assert shoppinglist.errors.get(:creationDate)
    assert shoppinglist.errors.get(:name)
    assert shoppinglist.errors.get(:user_id)
  end
  
  test "unique_shoppinglist" do
    shoppinglist = Shoppinglist.new(:creationDate => "2012-06-29",:name => "BBQ",
                          :user_id => 1)
    assert !shoppinglist.save
  end
  
  test "add and delete an Shoppinglist" do
    shoppinglist = Shoppinglist.new(:creationDate => "2012-06-29",:name => "Apfelkuchen",
                          :user_id => 1)
                          
    assert shoppinglist.save
    
    assert shoppinglist.delete
  end
end
