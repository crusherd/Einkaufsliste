require 'test_helper'

class PurchaseHistoriesControllerTest < ActionController::TestCase
  setup do
    @purchase_history = purchase_histories(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:purchase_histories)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create purchase_history" do
    assert_difference('PurchaseHistory.count') do
      post :create, purchase_history: { purchaseDate: @purchase_history.purchaseDate }
    end

    assert_redirected_to purchase_history_path(assigns(:purchase_history))
  end

  test "should show purchase_history" do
    get :show, id: @purchase_history
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @purchase_history
    assert_response :success
  end

  test "should update purchase_history" do
    put :update, id: @purchase_history, purchase_history: { purchaseDate: @purchase_history.purchaseDate }
    assert_redirected_to purchase_history_path(assigns(:purchase_history))
  end

  test "should destroy purchase_history" do
    assert_difference('PurchaseHistory.count', -1) do
      delete :destroy, id: @purchase_history
    end

    assert_redirected_to purchase_histories_path
  end
end
