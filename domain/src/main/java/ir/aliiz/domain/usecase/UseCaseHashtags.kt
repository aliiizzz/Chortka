package ir.aliiz.domain.usecase

import ir.aliiz.domain.model.HashtagDomain

abstract class UseCaseHashtags: UseCaseBase<Unit, List<HashtagDomain>>()