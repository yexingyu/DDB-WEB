/**
 * Created by x_ye on 8/10/2015.
 */
angular.module('ddbApp.models', [])

    /*
     * ProductModel
     */
    .factory('ProductModel', ['$rootScope', 'ProductService', function ($rootScope, ProductService) {
        return {
            like: function (product) {
                ProductService.like(product.id, function (response) {
                    if (response.status === 'SUCCESS' && response.data === 'Success') {
                        product.countLikes++;
                    }
                });
            },
            reviewHoveringOver: function (value, item) {
                item.review.overStar = value;
            },
            review: function (product) {
                ProductService.review(product.review, function (response) {
                    if (response.status === 'SUCCESS' && response.data === 'Success') {
                        product.countReviews++;
                        product.review.showMsg = 'message';
                        product.review.msg = 'Review success';
                    } else {
                        product.review.showMsg = 'error';
                        product.review.msg = response.data;
                    }
                });
            },
            fixLikeAndReviewOnProduct: function (product) {
                product.review = {
                    'productId': product.id,
                    'content': '',
                    'rating': 3,
                    'overStar': 3,
                    'showMsg': 'None',
                    'msg': ''
                };
            }
        };
    }])

    /*
     * StoreModel
     */
    .factory('StoreModel', ['$rootScope', '$route', 'ProductService', 'StoreService', 'LoginService', function ($rootScope, $route, ProductService, StoreService, LoginService) {
        return {
            fixFollowed: function (store, followedStores) {
                store.followed = false;
                if (followedStores !== undefined && followedStores.length > 0) {
                    angular.forEach(followedStores, function (followedStore) {
                        if (store.id === followedStore.id) {
                            store['followed'] = true;
                            return;
                        }
                    });
                }
            },
            follow: function (store) {
                StoreService.follow(store.id, function (response) {
                    if (response.status === 'SUCCESS') {
                        $route.reload();
                    } else if (response.status === 'NEED_LOGIN') {
                        LoginService.showLoginBox(function (profile) {
                            StoreService.follow(store.id, function (response) {
                                $route.reload();
                            });
                        });
                    }
                });
            },
            unfollow: function (store) {
                StoreService.unfollow(store.id, function (response) {
                    if (response.status === 'SUCCESS') {
                        $route.reload();
                    } else if (response.status === 'NEED_LOGIN') {
                        LoginService.showLoginBox(function (profile) {
                            StoreService.unfollow(store.id, function (response) {
                                $route.reload();
                            });
                        });
                    }
                });
            }
        };

    }])


    /*
     * HomeModel
     */
    .factory('HomeModel', ['$rootScope', function ($rootScope) {

    }]);
