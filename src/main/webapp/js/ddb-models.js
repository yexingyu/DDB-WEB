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
            unlike: function (product) {
                ProductService.unlike(product.id, function (response) {
                    if (response.status === 'SUCCESS' && response.data === 'Success') {
                        product.countUnlikes++;
                    }
                });
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
                    'showMsg': 'None',
                    'msg': ''
                };
            },
            fix: function (product) {
                // fix texts
                var texts = product.texts;
                product.texts = {};
                if (angular.isArray(texts) && texts.length > 0) {
                    texts.forEach(function (item) {
                        product.texts[item.language] = item;
                    });
                    angular.forEach($rootScope.constant.LANGUAGE, function (key, value) {
                        if (!product.texts[value]) {
                            product.texts[value] = texts[0];
                        }
                    });
                }
                texts = [];
                return product;
            }
        };
    }])

    /*
     * OrderModel
     */
    .factory('OrderModel', ['$rootScope', '$route', 'ProductService', 'StoreService', 'LoginService', function ($rootScope, $route, ProductService, StoreService, LoginService) {
        return {
            fix: function (order, profile) {
                order.memberId = profile.id;

                // set addresses for order
                order.addresses = [];
                var haveBillingAddress = false;
                var haveShippingAddress = false;
                if (angular.isArray(profile.addresses) && profile.addresses.length > 0) {
                    // set shipping address
                    for (var i = 0; i < profile.addresses.length; i++) {
                        if (profile.addresses[i].type === 'SHIPPING') {
                            var obj = {};
                            angular.copy(profile.addresses[i], obj);
                            obj.id = 0;
                            order.addresses.push(obj);
                            haveShippingAddress = true;
                            break;
                        }
                    }
                    if (haveShippingAddress === false) {
                        var obj = {};
                        angular.copy(profile.addresses[0], obj);
                        obj.id = 0;
                        obj.type = 'SHIPPING';
                        order.addresses.push(obj);
                    }

                    // set billing address
                    for (var i = 0; i < profile.addresses.length; i++) {
                        if (profile.addresses[i].type === 'BILLING') {
                            var obj = {};
                            angular.copy(profile.addresses[i], obj);
                            obj.id = 0;
                            order.addresses.push(obj);
                            haveBillingAddress = true;
                            break;
                        }
                    }
                    if (haveBillingAddress === false) {
                        var obj = {};
                        angular.copy(profile.addresses[0], obj);
                        obj.id = 0;
                        obj.type = 'BILLING';
                        order.addresses.push(obj);
                    }
                } else {
                    order.addresses.push({
                        type: 'SHIPPING'
                    });
                    order.addresses.push({
                        type: 'BILLING'
                    });
                }
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
     * TagModel
     */
    .factory('TagModel', ['$rootScope', '$route', 'ProductService', 'TagService', 'LoginService', function ($rootScope, $route, ProductService, TagService, LoginService) {
        return {
            fixFollowed: function (tag, followedTags) {
                tag.followed = false;
                if (followedTags !== undefined && followedTags.length > 0) {
                    angular.forEach(followedTags, function (followedTag) {
                        if (tag.id === followedTag.id) {
                            tag['followed'] = true;
                            return;
                        }
                    });
                }
            },
            followTag: function (tag) {
                TagService.followTag(tag.id, function (response) {
                    if (response.status === 'SUCCESS') {
                        $route.reload();
                    } else if (response.status === 'NEED_LOGIN') {
                        LoginService.showLoginBox(function (profile) {
                            TagService.follow(tag.id, function (response) {
                                $route.reload();
                            });
                        });
                    }
                });
            },
            unfollowTag: function (tag) {
                TagService.unfollowTag(tag.id, function (response) {
                    if (response.status === 'SUCCESS') {
                        $route.reload();
                    } else if (response.status === 'NEED_LOGIN') {
                        LoginService.showLoginBox(function (profile) {
                            TagService.unfollow(tag.id, function (response) {
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
