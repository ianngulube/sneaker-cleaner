package za.co.mafsoft.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import za.co.mafsoft.api.model.CartFiller;
import za.co.mafsoft.api.model.request.CartFillerRequest;
import za.co.mafsoft.api.service.CatalogService;
import za.co.mafsoft.api.service.UserService;

@Mapper(componentModel = "cdi")
public interface CartFillerRequestCartFillerMapper {
    @Mapping(expression = "java(userService.getOne(cartFillerRequest.getEmailOrMsisdn()))", target = "user")
    @Mapping(expression = "java(catalogService.getCatalog(cartFillerRequest.getCatalogId()))", target = "catalogItem")
    CartFiller fromCartFillerRequestToCartFiller(final CartFillerRequest cartFillerRequest,
                                                 final UserService userService,
                                                 final CatalogService catalogService);
}
